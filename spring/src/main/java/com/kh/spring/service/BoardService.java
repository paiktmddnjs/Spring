package com.kh.spring.service;

import com.kh.spring.model.vo.Board;
import com.kh.spring.model.vo.PageInfo;

import java.util.ArrayList;
import java.util.List;

public interface BoardService {
    int selectAllBoardCount();
    ArrayList<Board> selectAllBoard(PageInfo pi);

}