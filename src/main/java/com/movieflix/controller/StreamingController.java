package com.movieflix.controller;




import com.movieflix.controller.request.StreamingRequest;
import com.movieflix.controller.response.StreamingResponse;
import com.movieflix.entity.Streaming.Streaming;
import com.movieflix.mapper.StreamingMapper;
import com.movieflix.service.StreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movieflix/streaming")
@RequiredArgsConstructor
public class StreamingController {

    private final StreamingService streamingService;


    @GetMapping("/ListaCompleta")
    public ResponseEntity<List<StreamingResponse>> listarTodosStreamings(){
        List<StreamingResponse> lista = streamingService.ListarTodosStreamings().stream()
                .map(streaming -> StreamingMapper.toStreamingResponse(streaming))
                .toList();
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/CriarStreaming")
    public ResponseEntity <StreamingResponse> CriarStreaming(@RequestBody StreamingRequest streamingRequest) {
        Streaming streaming = StreamingMapper.toStreaming(streamingRequest);
        Streaming  streamingCriado  = streamingService.criarStreaming(streaming);
        return  ResponseEntity.status(HttpStatus.CREATED).body(StreamingMapper.toStreamingResponse(streamingCriado));
    }

    @DeleteMapping("/DeletarStreaming/{id}")
    public void deletarCategoria(@PathVariable Long id) {
        streamingService.deletarStreaming(id);

    }

    @GetMapping("/listarPorId/{id}")
    public ResponseEntity <List<StreamingResponse>> listarPorId(@PathVariable Long id) {
        Optional<Streaming> streamingPresente = streamingService.listarPorId(id).stream().findFirst();
        if (streamingPresente == null || streamingPresente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }return ResponseEntity.ok(streamingService.listarPorId(id).stream()
                .map(streaming -> StreamingMapper.toStreamingResponse(streaming))
                .toList());
    }

    @GetMapping("/listarPorNome/{nome}")
    public ResponseEntity <List<StreamingResponse>> listarPorNome(@PathVariable("nome") String name) {
        List<Streaming> streaming = streamingService.listarPorNome(name);
        if(streaming != null || streaming.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }return ResponseEntity.ok(streamingService.listarPorNome(name).stream()
                .map(cat -> StreamingMapper.toStreamingResponse(cat))
                .toList());

    }

    @PutMapping("/alterarCategoriaPorId/{id}")
    public ResponseEntity <StreamingResponse> alterarStreamingPorId(@PathVariable Long id,@RequestBody Streaming streaming) {
        Streaming streamingAlterar = streamingService.alterarStreamingPorId(id, streaming);
        StreamingResponse streamingAlterada = StreamingMapper.toStreamingResponse(streamingAlterar);
        return ResponseEntity.ok(streamingAlterada);
    }



}
