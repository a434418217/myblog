package wen.myblog.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wen.myblog.pojo.Type;

import java.util.List;

public interface TypeDao extends JpaRepository<Type,Integer> {
        Type findByName(String name);

        @Query("select t from Type t")
        List<Type> findSort(Pageable pageable);
}
