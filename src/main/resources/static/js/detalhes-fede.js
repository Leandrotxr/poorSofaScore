const urlParams = new URLSearchParams(window.location.search);
const federacaoNome = urlParams.get('nome');

if (!federacaoNome) {
    window.location.href = 'federacoes.html';
}

const API_URL = `/federacoes/${encodeURIComponent(federacaoNome)}`;

async function carregarDetalhesFederacao() {
    const nomeTitulo = document.getElementById('nome-federacao');
    const conteudoDiv = document.getElementById('conteudo-detalhes');

    try {
        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error('Erro na resposta do servidor ao buscar detalhes');
        }

        const federacao = await response.json();

        nomeTitulo.innerText = `🏛️ ${federacao.nome || 'Federação sem nome'}`;

        let arbitrosHTML = '<p style="color: #718096; font-style: italic; margin: 0;">Nenhum árbitro vinculado a esta federação.</p>';
        if (federacao.arbitros && federacao.arbitros.length > 0) {
            arbitrosHTML = '<ul style="margin: 0; padding-left: 20px; color: #4a5568;">';
            federacao.arbitros.forEach(arbitro => {
                arbitrosHTML += `<li style="margin-bottom: 5px; font-weight: 500;"> ${arbitro}</li>`;
            });
            arbitrosHTML += '</ul>';
        }

        let campeonatosHTML = '<p style="color: #718096; font-style: italic; margin: 0;">Nenhum campeonato organizado por esta federação.</p>';
        if (federacao.campeonatos && federacao.campeonatos.length > 0) {
            campeonatosHTML = '<ul style="margin: 0; padding-left: 20px; color: #4a5568;">';
            federacao.campeonatos.forEach(camp => {
                campeonatosHTML += `<li style="margin-bottom: 5px; font-weight: 500;"> ${camp}</li>`;
            });
            campeonatosHTML += '</ul>';
        }

        conteudoDiv.innerHTML = `
            <div class="detalhes-bloco" style="display: flex; flex-direction: column; gap: 15px;">
                <div class="info-linha">
                    <span class="info-label">Sigla da Entidade:</span> 
                    <span>${federacao.nome}</span>
                </div>
                
                <div class="info-linha" style="border-top: 1px solid #e2e8f0; padding-top: 15px; margin-top: 5px;">
                    <span class="info-label" style="display: block; margin-bottom: 10px;">👔 Quadro de Árbitros:</span>
                    ${arbitrosHTML}
                </div>

                <div class="info-linha" style="border-top: 1px solid #e2e8f0; padding-top: 15px; margin-top: 5px;">
                    <span class="info-label" style="display: block; margin-bottom: 10px;">🏆 Campeonatos Sob Chancela:</span>
                    ${campeonatosHTML}
                </div>
            </div>
        `;

    } catch (erro) {
        console.error('Erro ao renderizar dados:', erro);
        nomeTitulo.innerText = 'Erro ao carregar';
        conteudoDiv.innerHTML = `
            <div class="status-erro">
                <strong>Erro ao carregar detalhes!</strong><br>
                Certifique-se de que o banco de dados possui esta federação e que o Spring Boot está retornando o DTO corretamente.
            </div>
        `;
    }
}

carregarDetalhesFederacao();