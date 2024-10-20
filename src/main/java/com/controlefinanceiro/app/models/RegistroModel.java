package com.controlefinanceiro.app;

import com.controlefinanceiro.app.RegistroRepository;
import com.controlefinanceiro.app.RegistroDTO;
import com.controlefinanceiro.app.RegistroMapper;

public class RegistroModel {
    private RegistroRepository registroRepository;
    private RegistroMapper registroMapper;

    public RegistroModel(
        RegistroRepository registroRepository,
        RegistroMapper registroMapper
    ) {
        this.registroRepository = registroRepository;
        this.registroMapper = registroMapper;
    }

    public void save(RegistroDTO registroDTO) throws Exception{
        this.registroRepository.save(
            this.registroMapper.toDomain(registroDTO)
        );
    }
}
