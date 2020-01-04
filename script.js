const getP1 = document.querySelector('#get_p1');
const getP2 = document.querySelector('#get_p2');
const getP3 = document.querySelector('#get_p3');
const getP4 = document.querySelector('#get_p4');

const sendHttpRequest = (method, url, data) => {
    return fetch(url, {
        method: method,
        mode: "no-cors",
        body: JSON.stringify(data),
        headers: data ? { 'Content-Type': 'application/json', 'Accept': 'application/json' } : {}
    }).then(response => {
        if (response.status >= 400) {
            // !response.ok
            return response.json().then(errResData => {
                const error = new Error('Something went wrong!');
                error.data = errResData;
                throw error;
            });
        }
        return response.json();
    });
};

const getData = (url) => {
    sendHttpRequest('GET', url).then(url => {
        console.log(url);
    });
};

getP1.addEventListener('click', () => {
    getData('http://localhost:4567/api/sveriges-radio/getsongs/132')
})

getP2.addEventListener('click', () => {
    getData('http://localhost:4567/api/sveriges-radio/getsongs/163')
})

getP3.addEventListener('click', () => {
    getData('http://localhost:4567/api/sveriges-radio/getsongs/164')
})

getP4.addEventListener('click', () => {
    getData('http://localhost:4567/api/sveriges-radio/getsongs/207')
})