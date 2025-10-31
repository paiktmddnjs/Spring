package com.kh.spring.service;

import com.kh.spring.model.vo.Attachment;
import com.kh.spring.model.vo.Board;
import com.kh.spring.model.vo.Category;
import com.kh.spring.model.vo.PageInfo;

import java.util.ArrayList;
import java.util.List;

public interface BoardService {
    int selectAllBoardCount();
    List<Board> selectAllBoard(PageInfo pi);
    int increaseCount(int boardNo);
    Board selectBoardByBoardNo(int boardNo);
    ArrayList<Category> selectAllCategory();
    int insertBoard(Board board, Attachment at);
    Attachment selectAttachment(int boardNo);
}