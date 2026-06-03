const urlParams = new URLSearchParams(window.location.search);
const equipeNome = urlParams.get('nome');

if (!equipeNome) {
    window.location.href = 'equipes.html';
}

const API_URL = `/equipes/${encodeURIComponent(equipeNome)}`;

async function carregarDetalhesEquipe() {
    const nomeTitulo = document.getElementById('nome-equipe');
    const conteudoDiv = document.getElementById('conteudo-detalhes');

    try {
        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error('Erro na resposta do servidor ao buscar detalhes');
        }

        const equipe = await response.json();

        nomeTitulo.innerText = `🛡️ ${equipe.nome || 'Equipe sem nome'}`;

        let jogadoresHTML = '<p style="color: #718096; font-style: italic; margin: 0;">Nenhum jogador listado.</p>';
        if (equipe.jogadores && equipe.jogadores.length > 0) {
            jogadoresHTML = '<ul style="margin: 0; padding-left: 20px; color: #4a5568;">';
            equipe.jogadores.forEach(jogador => {
                jogadoresHTML += `<li style="margin-bottom: 3px;"> ${jogador}</li>`;
            });
            jogadoresHTML += '</ul>';
        }

        let campeonatosHTML = '<p style="color: #718096; font-style: italic; margin: 0;">Não participa de nenhum campeonato.</p>';
        if (equipe.campeonatos && equipe.campeonatos.length > 0) {
            campeonatosHTML = '<ul style="margin: 0; padding-left: 20px; color: #4a5568;">';
            equipe.campeonatos.forEach(camp => {
                campeonatosHTML += `<li style="margin-bottom: 3px;"> ${camp}</li>`;
            });
            campeonatosHTML += '</ul>';
        }

        let patrociniosHTML = '<p style="color: #718096; font-style: italic; margin: 0;">Sem patrocinadores vinculados a esta equipe.</p>';

        if (equipe.patrocinios && equipe.patrocinios.length > 0) {
            patrociniosHTML = `
                <table style="width: 100%; border-collapse: collapse; margin-top: 8px; font-size: 0.95em;">
                    <thead>
                        <tr style="background-color: #f8fafc; border-bottom: 2px solid #e2e8f0; text-align: left;">
                            <th style="padding: 10px; color: #4a5568;">Patrocinador</th>
                            <th style="padding: 10px; color: #4a5568; text-align: right;">Valor do Contrato</th>
                        </tr>
                    </thead>
                    <tbody>
            `;

            equipe.patrocinios.forEach(patrocinio => {
                const nomePatrocinador = patrocinio.patrocinador || 'Patrocinador Oculto';
                const valorContrato = patrocinio.valor
                    ? `R$ ${patrocinio.valor.toLocaleString('pt-BR')}`
                    : 'Não informado';

                patrociniosHTML += `
                    <tr style="border-bottom: 1px solid #edf2f7;">
                        <td style="padding: 10px; font-weight: 500; color: #2d3748;"> ${nomePatrocinador}</td>
                        <td style="padding: 10px; text-align: right; font-weight: bold; color: #2f855a;">${valorContrato}</td>
                    </tr>
                `;
            });

            patrociniosHTML += `
                    </tbody>
                </table>
            `;
        }

        conteudoDiv.innerHTML = `
            <div class="detalhes-bloco" style="display: flex; flex-direction: column; gap: 18px;">
                <div class="info-linha">
                    <span class="info-label">📍 Sede / Cidade:</span> 
                    <span>${equipe.sede || 'Não informada'}</span>
                </div>
                
                <div class="info-linha">
                    <span class="info-label">📅 Ano de Fundação:</span> 
                    <span>${equipe.fundacao ? equipe.fundacao : 'Não informado'}</span>
                </div>
                
                <div class="info-linha">
                    <span class="info-label">📋 Técnico:</span> 
                    <span>${equipe.tecnico || 'Sem técnico definido'}</span>
                </div>
                
                <div class="info-linha" style="border-top: 1px solid #e2e8f0; padding-top: 15px;">
                    <span class="info-label" style="display: block; margin-bottom: 8px;">👥 Elenco / Jogadores:</span>
                    ${jogadoresHTML}
                </div>

                <div class="info-linha" style="border-top: 1px solid #e2e8f0; padding-top: 15px;">
                    <span class="info-label" style="display: block; margin-bottom: 8px;">🏆 Campeonatos Disputados:</span>
                    ${campeonatosHTML}
                </div>

                <div class="info-linha" style="border-top: 1px solid #e2e8f0; padding-top: 15px;">
                    <span class="info-label" style="display: block; margin-bottom: 5px;">🤝 Patrocinadores:</span>
                    ${patrociniosHTML}
                </div>
            </div>
        `;

    } catch (erro) {
        console.error('Erro ao renderizar dados:', erro);
        nomeTitulo.innerText = 'Erro ao carregar';
        conteudoDiv.innerHTML = `
            <div class="status-erro">
                <strong>Erro ao carregar detalhes!</strong><br>
                Certifique-se de que o banco de dados possui esta equipe e que o endpoint <code>GET ${API_URL}</code> está ativo.
            </div>
        `;
    }
}

carregarDetalhesEquipe();