package mks.myworkspace.english.toeic.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import mks.myworkspace.english.toeic.entity.AssessmentGrading;
import mks.myworkspace.english.toeic.entity.ItemGrading;
import mks.myworkspace.english.toeic.repository.AppRepository;
import mks.myworkspace.english.toeic.repository.ItemGradingRepository;
import mks.myworkspace.english.toeic.service.ItemGradingService;

@Service
@Slf4j
public class ItemGradingServiceImpl implements ItemGradingService{
	@Autowired
	private ItemGradingRepository repo;
	
	@Autowired
	@Getter
	AppRepository appRepo;

	@Override
	public ItemGradingRepository getRepo() {
		return repo;
	}
	
	@Override
	public Optional<ItemGrading> findById(Long id) {
		return repo.findById(id);
	}

	@Override
	public ItemGrading saveOrUpdate(ItemGrading itemGrading) {  
		Long id = appRepo.saveOrUpdate(itemGrading);
		if (id != null) {
			itemGrading.setItemGradingId(id);
		}
		return itemGrading;
	}
}
