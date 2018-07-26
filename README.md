#增加spring cloud 分布式和负载均衡
1.初探
  Eureka：
   spring-cloud-starter-eureka：服务客户端（服务的真实提供者）
     @EnableEurekaClient就成了服务客户端
   spring-cloud-starter-eureka-sever：服务注册中心（只供服务的注册和发现）
     在spring 启动类上注解 启动就是服务注册中心
     @EnableEurekaServer
  ribbon：
     负责调用服务客户端。结合restTemplate可实现负载均衡
     用法：
     1.类加注解@EnableDiscoveryClient
     2.负载均衡的restTemplate
         @Bean
         @LoadBalanced
         RestTemplate restTemplate() {
             return new RestTemplate();
         }
       3.调用：
       public String hiService(String name) {
               return restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);
           }
  Feign：
     也是服务的调用者。封装了ribbion。
     用法：
     1.加注解@EnableFeignClients
     2.定义调用接口
        @FeignClient(value = "service-hi")
        public interface SchedualServiceHi {
            @RequestMapping(value = "/hi",method = RequestMethod.GET)
            String sayHiFromClientOne(@RequestParam(value = "name") String name);
        }
        3.直接调用接口
        @RestController
        public class HiController {
            @Autowired
            SchedualServiceHi schedualServiceHi;
            @RequestMapping(value = "/hi",method = RequestMethod.GET)
            public String sayHi(@RequestParam String name){
                return schedualServiceHi.sayHiFromClientOne(name);
            }
        }
        
  hystrix：
       熔断器，用来配合ribbon和feign调用不可用的服务的熔断策略。
       用法：
        ribbon内使用：
       @Service
       public class HelloService {
           @Autowired
           RestTemplate restTemplate;
           @HystrixCommand(fallbackMethod = "hiError")
           public String hiService(String name) {
               return restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);
           }
           public String hiError(String name) {
               return "hi,"+name+",sorry,error!";
           }
       }
       feign内使用：
       配置文件feign.hystrix.enabled=true
       @FeignClient(value = "service-hi",fallback = SchedualServiceHiHystric.class)
       public interface SchedualServiceHi {
           @RequestMapping(value = "/hi",method = RequestMethod.GET)
           String sayHiFromClientOne(@RequestParam(value = "name") String name);
       }
       @Component
       public class SchedualServiceHiHystric implements SchedualServiceHi {
           @Override
           public String sayHiFromClientOne(String name) {
               return "sorry "+name;
           }
       }
  zuul:
      Zuul的主要功能是路由转发和过滤器.
      用法：
          加注解@EnableZuulProxy
          加配置：
              zuul:
                routes:
                  api-a:
                    path: /api-a/**
                    serviceId: service-ribbon
                  api-b:
                    path: /api-b/**
                    serviceId: service-feign
      token验证：
      
      @Component
      public class MyFilter extends ZuulFilter{
      
          private static Logger log = LoggerFactory.getLogger(MyFilter.class);
          @Override
          public String filterType() {
              return "pre";
          }
      
          @Override
          public int filterOrder() {
              return 0;
          }
      
          @Override
          public boolean shouldFilter() {
              return true;
          }
      
          @Override
          public Object run() {
              RequestContext ctx = RequestContext.getCurrentContext();
              HttpServletRequest request = ctx.getRequest();
              log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
              Object accessToken = request.getParameter("token");
              if(accessToken == null) {
                  log.warn("token is empty");
                  ctx.setSendZuulResponse(false);
                  ctx.setResponseStatusCode(401);
                  try {
                      ctx.getResponse().getWriter().write("token is empty");
                  }catch (Exception e){}
      
                  return null;
              }
              log.info("ok");
              return null;
          }
      }