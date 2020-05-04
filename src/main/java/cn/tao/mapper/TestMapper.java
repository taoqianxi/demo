package cn.tao.mapper;

import cn.tao.entity.Test;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.*;

import java.util.List;

@Mapper
public interface TestMapper {

    List<Test> listTest();

    void insert(Test test);
}
