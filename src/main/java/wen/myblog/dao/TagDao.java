package wen.myblog.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wen.myblog.pojo.Tag;

import java.util.List;

public interface TagDao extends JpaRepository<Tag, Integer> {
    Tag findByName(String name);

    @Query("select t from Tag t")
    List<Tag> findSort(Pageable pageable);
}
