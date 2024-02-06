package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.EpisodioModel;
import br.com.alura.screenmatch.model.SerieModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SerieRepository extends JpaRepository<SerieModel, Long> {
    Optional<SerieModel> findByTituloContainingIgnoreCase(String nomeSerie);

    List<SerieModel> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);

    List<SerieModel> findTop5ByOrderByAvaliacaoDesc();

    List<SerieModel> findByGenero(Categoria categoria);

    List<SerieModel> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(Integer totalTemporadas, Double avaliacao);

    @Query(value = """
            select s from SerieModel s
             WHERE s.totalTemporadas <= :totalTemporadas
               and s.avaliacao >= :avaliacao
            """)
    List<SerieModel> seriesPorTemporadaEAvaliacao(Integer totalTemporadas, Double avaliacao);

    @Query(value = """
            SELECT e FROM SerieModel s
              JOIN s.episodioModels e 
             WHERE e.titulo ILIKE %:trechoEpisodio%
            """)
    List<EpisodioModel> episodiosPorTrecho(String trechoEpisodio);

    @Query(value = """
            SELECT e FROM SerieModel s
              JOIN s.episodioModels e
             WHERE s = :serie
             ORDER BY e.avaliacao DESC LIMIT 5
            """)
    List<EpisodioModel> topEpisodiosPorSerie(SerieModel serie);

    @Query(value = """
            SELECT e FROM SerieModel s
              JOIN s.episodioModels e
             WHERE s = :serie
               AND YEAR(e.dataLancamento) >= :anoLancamento 
            """)
    List<EpisodioModel> episodiosPorSerieEAno(SerieModel serie, int anoLancamento);
}
