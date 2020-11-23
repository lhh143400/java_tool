package com.ylz.springboot.modules.elasticsearch.controller;

import com.ylz.springboot.modules.elasticsearch.service.EsRepositoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lhh
 * @Date 2019/9/17 0:42
 */
@RestController
@RequestMapping("es_repo")
public class EsRepositoryController {
}
