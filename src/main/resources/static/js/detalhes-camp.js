const urlParams = new URLSearchParams(window.location.search);
const campeonatoNome = urlParams.get('nome');

if (!campeonatoNome) {
    window.location.href = 'campeonatos.html';
}

const API_URL = `/campeonatos/${encodeURIComponent(campeonatoNome)}`;

async function carregarDetalhes() {
    const nomeTitulo = document.getElementById('nome-campeonato');
    const conteudoDiv = document.getElementById('conteudo-detalhes');

    try {
        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error('Erro na resposta do servidor ao buscar detalhes');
        }

        const campeonato = await response.json();

        nomeTitulo.innerText = `🏆 ${campeonato.nome || 'Campeonato sem nome'}`;

        let equipesHTML = '<p style="color: #718096; font-style: italic;">Nenhuma equipe cadastrada neste campeonato.</p>';

        if (campeonato.equipes && campeonato.equipes.length > 0) {
            equipesHTML = '<ul style="margin: 0; padding-left: 20px; color: #4a5568;">';
            campeonato.equipes.forEach(equipe => {
                equipesHTML += `<li style="margin-bottom: 5px; font-weight: 500;"> ${equipe}</li>`;
            });
            equipesHTML += '</ul>';
        }

        conteudoDiv.innerHTML = `
            <div class="detalhes-bloco" style="display: flex; flex-direction: column; gap: 15px;">
                <div class="info-linha">
                    <span class="info-label">📍 Local / Região:</span> 
                    <span>${campeonato.local || 'Não informado'}</span>
                </div>
                
                <div class="info-linha">
                    <span class="info-label">💰 Premiação:</span> 
                    <span style="color: #2f855a; font-weight: bold;">
                        ${campeonato.premio ? `$ ${campeonato.premio.toLocaleString('pt-BR')}` : 'Sem premiação'}
                    </span>
                </div>
                
                <div class="info-linha">
                    <span class="info-label">🏛️ Federação Responsável:</span> 
                    <span>${campeonato.federacao || 'Não informada'}</span>
                </div>
                
                <div class="info-linha" style="border-top: 1px solid #e2e8f0; padding-top: 15px; margin-top: 5px;">
                    <span class="info-label" style="display: block; margin-bottom: 10px;">🏃 Equipes Participantes:</span>
                    ${equipesHTML}
                </div>
            </div>
        `;

    } catch (erro) {
        console.error('Erro ao renderizar dados:', erro);
        nomeTitulo.innerText = 'Erro ao carregar';
        conteudoDiv.innerHTML = `
            <div class="status-erro">
                <strong>Erro ao carregar detalhes!</strong><br>
                Certifique-se de que o banco de dados possui este campeonato e que o Spring Boot está retornando o DTO corretamente.
            </div>
        `;
    }
}

carregarDetalhes();