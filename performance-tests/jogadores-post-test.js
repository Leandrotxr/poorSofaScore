import http from 'k6/http';
import { check } from 'k6';

export let options = {
    vus: 20,
    duration: '10s',
};

export default function () {

    // corpo da requisição (JSON)
    let payload = JSON.stringify({
        nome: "JogadorTeste_" + Math.random(),
        cpf: Math.random().toString().slice(2, 11),
        idade: 25,
        nacionalidade: "Brasil",
        posicao: "Atacante"
    });

    let params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    let res = http.post(
        'http://localhost:3000/jogadores/adicionarJogador',
        payload,
        params
    );

    check(res, {
        'status é 201': (r) => r.status === 201,
        'tempo < 2000ms': (r) => r.timings.duration < 2000,
    });
}