package com.aqzscn.www.movie.controller;

import com.aqzscn.www.global.controller.BaseController;
import com.aqzscn.www.global.domain.dto.ReturnVo;
import com.aqzscn.www.movie.service.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(tags = "电影服务")
@RequestMapping("/mv")
public class MovieController extends BaseController {

    private final MovieService movieService;

    public MovieController(HttpServletRequest request, HttpServletResponse response, MovieService movieService) {
        super(request, response);
        this.movieService = movieService;
    }

    @ApiOperation("立即更新电影信息")
    @PutMapping("/new-movie")
    public ReturnVo updateMovieInfo() throws RuntimeException {
        return response(movieService.updateMovieInfo());
    }

}
