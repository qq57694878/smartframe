package com.ljl.gsapputil.controller;

import com.ljl.gsapputil.model.AreaCodeEntity;
import com.ljl.gsapputil.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AreaController {
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @RequestMapping("getAllArea")
    public Object getAllArea(){
        Long total = jdbcTemplate.queryForObject("select count(1) num from area_code where area_level='2' and area_status='1'",Long.class);
        List<Map<String,Object>> rlist = new ArrayList<Map<String,Object>>();

        List<AreaCodeEntity> level0List = getAreaList("0");
        List<AreaCodeEntity> level1List = getAreaList("1");
        List<AreaCodeEntity> level2List = getAreaList("2");

        for(AreaCodeEntity o:level0List){
            Map<String,Object> m = new HashMap<String,Object>();
            m.put("k",o.getAreaCode());
            m.put("v",o.getProvince());
            m.put("c",new ArrayList<Map<String,Object>>());
            rlist.add(m);
        }
        for(AreaCodeEntity o:level1List){
            String province = o.getProvince();
            int index1=  getIndex(rlist,province);
            if(index1>-1){
                List<Map<String,Object>> list1 = ( List<Map<String,Object>>)rlist.get(index1).get("c");
                Map<String,Object> m = new HashMap<String,Object>();
                m.put("k",o.getAreaCode());
                m.put("v",o.getCity());
                m.put("c",new ArrayList<Map<String,Object>>());
                list1.add(m);
            }
        }
        for(AreaCodeEntity o:level2List){
            String city = o.getCity();
            int[]ia=  getIndexCity(rlist,city);
            if(ia[0]>-1&&ia[1]>-1){
                List<Map<String,Object>> list1 = ( List<Map<String,Object>>)rlist.get(ia[0]).get("c");
                List<Map<String,Object>> list2 = ( List<Map<String,Object>>)list1.get(ia[1]).get("c");
                Map<String,Object> m = new HashMap<String,Object>();
                m.put("k",o.getAreaCode());
                m.put("v",o.getCountry());
                list2.add(m);
            }
        }
         return rlist;
    }
    private int getIndex(List<Map<String,Object>> rlist,String name){
        int index=-1;
          for(Map<String,Object>m:rlist){
              index++;
              if(name.equals(m.get("v"))){
                   break;
              }
          }
          return index;
    }
    private int[] getIndexCity(List<Map<String,Object>> rlist,String name){
        int index1=-1;
        int index2=-1;
        outer:
        for(Map<String,Object>m:rlist){
            index1++;
            List<Map<String,Object>> list1 = ( List<Map<String,Object>>)m.get("c");
            index2=-1;
            for(Map<String,Object>m1:list1){
                index2++;
                    if(name.equals(m1.get("v"))){
                        break outer;
                    }

            }

        }
        return new int[]{index1,index2};
    }
    private List<AreaCodeEntity>getAreaList(String level){
        Sort sort = new Sort(Sort.Direction.ASC,"areaCode");
       return  areaRepository.findAll(new Specification<AreaCodeEntity>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<AreaCodeEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                Predicate p1 = criteriaBuilder.equal(root.get("areaLevel"),level);
                Predicate p2 = criteriaBuilder.equal(root.get("areaStatus"),"1");
                Predicate p3=criteriaBuilder.and(p1,p2);
                predicates.add(p1);
                return  criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        },sort);
    }
}
