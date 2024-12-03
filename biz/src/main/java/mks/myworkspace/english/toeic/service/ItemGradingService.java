package mks.myworkspace.english.toeic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.ItemGrading;
import mks.myworkspace.english.toeic.repository.ItemGradingRepository;

@Service
public interface ItemGradingService {
	ItemGradingRepository getRepo();
	
	ItemGrading saveOrUpdate(ItemGrading itemGrading);
	
	Optional<ItemGrading> findById(Long id);

}
