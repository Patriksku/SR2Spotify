const getBtn = document.getElementById('get_btn');

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

const getData = () => {
    sendHttpRequest('GET', 'http://localhost:4567/api/sveriges-radio/getsongs/207').then(responseData => {
        console.log(responseData);
    });
};

getBtn.addEventListener('click', getData);