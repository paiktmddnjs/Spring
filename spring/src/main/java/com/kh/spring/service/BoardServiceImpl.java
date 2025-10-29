package com.kh.spring.service;

import com.kh.spring.model.mapper.BoardMapper;
import com.kh.spring.model.vo.Attachment;
import com.kh.spring.model.vo.Board;
import com.kh.spring.model.vo.Category;
import com.kh.spring.model.vo.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final SqlSession sqlSession;

    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper, SqlSession sqlSession){

        this.boardMapper = boardMapper;
        this.sqlSession = sqlSession;
    }

    @Override
    public ArrayList<Board> selectAllBoard(PageInfo pi) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());

        //List<Board> list = boardMapper.selectAllBoard(rowBounds);
        List<Board> list = sqlSession.selectList("com.kh.spring.model.mapper.BoardMapper.selectAllBoard", null, rowBounds);
        return new ArrayList<>(list);
    }

    @Override
    public int increaseCount(int boardNo) {
        return boardMapper.increaseCount(boardNo);
    }

    @Override
    public int selectAllBoardCount() {
        return boardMapper.selectAllBoardCount();
    }


    @Override
    public Board selectBoardByBoardNo(int boardNo) {
        return boardMapper.selectBoardByBoardNo(boardNo);
    }

    public ArrayList<Category> selectAllCategory() {
        return boardMapper.selectAllCategory();
    }

    public int insertBoard(Board board, Attachment at) {

        return boardMapper.insertBoard(board ,at);
    }

}