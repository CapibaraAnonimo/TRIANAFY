package com.salesianostriana.dam.trianafy.service;

import com.salesianostriana.dam.trianafy.model.AddedTo;
import com.salesianostriana.dam.trianafy.repos.AddedToRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddedToService {
    private final AddedToRepository addedToRepository;

    public void save(AddedTo addedTo) {
        addedToRepository.save(addedTo);
    }
}
