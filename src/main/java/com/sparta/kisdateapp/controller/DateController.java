package com.sparta.kisdateapp.controller;


import com.sparta.kisdateapp.dto.DateRequestDto;
import com.sparta.kisdateapp.dto.DateResponseDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DateController {
    private final JdbcTemplate jdbcTemplate;

    public DateController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/dateaa")
    public DateResponseDto createDate(@RequestBody DateRequesDto requesDto) {
        Date date = new Date(requesDto);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO date (username, contents) VALUES (?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, date.getUsername());
                    preparedStatement.setString(2, date.getContents());
                    return preparedStatement;
                },
                keyHolder);

        Long id = keyHolder.getKey().longValue();
        date.setId(id);

        DateResponseDto dateResponseDto = new DateResponseDto(date);

        return dateResponseDto;
    }

    @GetMapping("/dateaa")
    public List<DateResponseDto> getDateaa() {
        String sql = "SELECT * FROM date";

        return jdbcTemplate.query(sql, new RowMapper<DateResponseDto>() {
            @Override
            public DateResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {

                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String contents = rs.getString("contents");
                return new DateResponseDto(id, username, contents);
            }
        });
    }

    @PutMapping("/dateaa/{id}")
    public Long updateDate(@PathVariable Long id, @RequestBody DateRequestDto requestDto) {
        Date date = findById(id);
        if (date != null) {
            String sql = "UPDATE date SET username = ?, contents = ? WHERE id = ?";
            jdbcTemplate.update(sql, requestDto.getUsername(), requestDto.getContents(), id);

            return id;
        } else {
            throw new IllegalArgumentException("==");
        }
    }

    @DeleteMapping("/dateaa/{id}")
    public Long deleteDate(@PathVariable Long id) {
        Date date = findById(id);
        if(date != null) {
            String sql = "DELETE FROM date WHERE id = ?";
            jdbcTemplate.update(sql, id);

            return id;
        } else {
            throw new IllegalArgumentException("==");
        }
    }

    private Date findById(Long id) {
        String sql = "SELECT * FROM date WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Date date = new Date();
                date.setUsername(resultSet.getString("username"));
                date.setContents(resultSet.getString("contents"));
                return date;
            } else {
                return null;
            }
        }, id);
    }
}
