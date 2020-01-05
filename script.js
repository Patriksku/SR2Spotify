const getP1 = document.querySelector('#get_p1');
const getP2 = document.querySelector('#get_p2');
const getP3 = document.querySelector('#get_p3');
const getP4 = document.querySelector('#get_p4');
const spotifyLogin = document.querySelector('#spotify_user');

const getData = (url) => {
    $(document).ready(function(){
        $.ajax({
            url: url
        }).then(function(data) {
            $('.channel_id').html("Kanal: " + data.channelname);
            $('.title').html("Nuvarande låt:  " + data.previoustitle);
            $('.artist').html("Artist:  " + data.previousartist);
            $('.album').html("Album:  " + data.previousalbum);
            $('.next_title').html("Nästa låt: " + data.nexttitle);
            $('.next_artist').html("Nästa artist: " + data.nextartist);
            $('.next_album').html("Nästa album:  " + data.nextalbum);
        });
    });
    console.log(url);
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

spotifyLogin.addEventListener('click', () => {
    getData('http://localhost:4567/api/spotify/authuser')
})