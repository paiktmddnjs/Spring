package com.kh.spring.model.mapper;

import com.kh.spring.model.vo.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;


import java.util.List;

@Mapper
public interface BoardMapper {
    List<Board> selectAllBoard(RowBounds rowBounds);
    int selectAllBoardCount();
    int increaseCount(@Param("boardNo") int boardNo);
    Board selectBoardByBoardNo(@Param("boardNo") int boardNo);
}
