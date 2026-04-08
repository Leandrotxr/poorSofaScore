import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    vus: 1000, // usuários simultâneos
    duration: '10s', // por x segundos
};

export default function () {
    let res = http.get('http://localhost:3000/jogadores');

    check(res, {
        'status é 200': (r) => r.status === 200,
        'tempo < 2000ms': (r) => r.timings.duration < 2000,
    });
    
}