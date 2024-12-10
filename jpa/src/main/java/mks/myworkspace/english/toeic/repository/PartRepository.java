package mks.myworkspace.english.toeic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mks.myworkspace.english.toeic.entity.Part;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    // Custom query methods nếu cần
}
