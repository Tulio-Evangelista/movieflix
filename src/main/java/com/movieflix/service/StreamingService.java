package com.movieflix.service;

import com.movieflix.entity.Streaming.Streaming;
import com.movieflix.repository.StreamingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StreamingService {


    public StreamingRepository streamingRepository;

        public StreamingService(StreamingRepository streamingRepository) {
            this.streamingRepository = streamingRepository;
    }


    public List<Streaming> ListarTodosStreamings() {
        return streamingRepository.findAll();
    }


    public Streaming criarStreaming(Streaming category) {
        return streamingRepository.save(category);
    }

    public void deletarStreaming(Long id) {
        streamingRepository.deleteById(id);
    }


    public List<Streaming> listarPorId(Long id) {
        Optional<Streaming> categoryPresente = streamingRepository.findById(id);
        if (categoryPresente.isPresent()) {
            return categoryPresente.stream().toList();

        }return null;
    }

    public List<Streaming> listarPorNome(String name) {
        return streamingRepository.findAll().stream()
                .filter(category -> category.getName().equalsIgnoreCase(name))
                .toList();
    }

    public Streaming alterarStreamingPorId(Long id, Streaming streaming) {
        return streamingRepository.findById(id)
                .map(existingStreaming -> {
                    existingStreaming.setName(streaming.getName());
                    return streamingRepository.save(existingStreaming);
                })
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o ID: " + id));
    }

}
