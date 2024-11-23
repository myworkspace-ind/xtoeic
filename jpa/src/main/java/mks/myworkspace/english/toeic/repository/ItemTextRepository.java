package mks.myworkspace.english.toeic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mks.myworkspace.english.toeic.entity.ItemText;

@Repository
public interface ItemTextRepository extends JpaRepository<ItemText, Long> {
    // Custom query methods nếu cần
}
