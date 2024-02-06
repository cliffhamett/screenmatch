package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.SerieModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SerieRepository extends JpaRepository<SerieModel, Long> {
    Optional<SerieModel> findByTituloContainingIgnoreCase(String nomeSerie);

    List<SerieModel> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);

}
