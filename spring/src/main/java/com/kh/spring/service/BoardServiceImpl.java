package com.kh.spring.service;

import com.kh.spring.model.mapper.BoardMapper;
import com.kh.spring.model.vo.Board;
import com.kh.spring.model.vo.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;


@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private SqlSession sqlSession;

    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper){

        this.boardMapper = boardMapper;

    }

    @Override
    public ArrayList<Board> selectAllBoard(PageInfo pi) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());

        List<Board> list = sqlSession.selectList("BoardMapper.selectAllBoard", null, rowBounds);
        return new ArrayList<>(list);
    }

    @Override
    public int selectAllBoardCount() {
        return boardMapper.selectAllBoardCount();
    }


}