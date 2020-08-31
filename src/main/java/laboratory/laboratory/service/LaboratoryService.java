package laboratory.laboratory.service;

import laboratory.laboratory.domain.Laboratory;
import laboratory.laboratory.repository.LaboratoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LaboratoryService{
    private final LaboratoryRepository laboratoryRepository;

    public LaboratoryService(LaboratoryRepository laboratoryRepository){
        this.laboratoryRepository = laboratoryRepository;
    }

    @Transactional
    public Laboratory registerLaboratory(Laboratory laboratory){
        return this.laboratoryRepository.save(laboratory);
    }
}
