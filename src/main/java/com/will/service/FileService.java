package com.will.service;


import com.will.domain.entity.FileEntity;
import com.will.domain.repository.FileRepository;
import com.will.dto.FileDto;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;


@Service
public class FileService {
    private FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Transactional
    public Long saveFile(FileDto fileDto) {
        return fileRepository.save(fileDto.toEntity()).getId();
    }

    @Transactional
    public FileDto getFile(Long id) {
        FileEntity file = fileRepository.findById(id).get();

        FileDto fileDto = FileDto.builder()
                .id(id)
                .origFilename(file.getOrigFilename())
                .filename(file.getFilename())
                .filePath(file.getFilePath())
                .build();
        return fileDto;
    }
    @Transactional
    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
}
    
}