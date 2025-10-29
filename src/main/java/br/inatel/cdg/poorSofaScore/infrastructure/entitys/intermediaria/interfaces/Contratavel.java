package br.inatel.cdg.poorSofaScore.infrastructure.entitys.intermediaria.interfaces;

import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Equipe;
import br.inatel.cdg.poorSofaScore.infrastructure.entitys.pessoa_juridica.Federacao;

public interface Contratavel {

    default void contratar(Equipe equipe) {

    }

    default void contratar(Federacao federacao) {

    }
}
