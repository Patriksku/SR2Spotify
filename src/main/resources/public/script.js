//Här hämtar vi våra låttexter från de låtar som vi kan hitta

const getLyrics =(url)=> {
    $(document).ready(function(){
        $.ajax({
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            type: 'GET',
            url: url,
        }).then(function(data) {
            $('.lyrics').html(data.text);
        });
    });
    console.log(url)
};

//Här skapas variabler för alla SR-radiostationer

const getP1 = document.querySelector('#get_p1');
const getP2 = document.querySelector('#get_p2');
const getP3 = document.querySelector('#get_p3');
const getP4 = document.querySelector('#get_p4');

//Här får vi datan från servern i JSON-format, som vi sedan skriver ut i html-form

const getData = (url) => {
    $(document).ready(function(){
        $.ajax({
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            type: 'GET',
            url: url,
        }).then(function(data) {
            $('.channel_id').html("Kanal: " + data.channelname);
            $('.title').html("Nuvarande låt:  " + data.title);
            $('.artist').html("Artist:  " + data.artist);
            $('.album').html("Album:  " + data.album);
            $('.next_title').html("Nästa låt: " + data.nexttitle);
            $('.next_artist').html("Nästa artist: " + data.nextartist);
            $('.next_album').html("Nästa album:  " + data.nextalbum);
        });
    });
    console.log(url);
};

//Här skapas funktioner för att när användaren trycker på knappen, hämtar man data från servern och då hämtar vi de olika kanalerna, låttexten till den aktuella låten och låt URI

getP1.addEventListener('click', () => {
    getData('http://localhost:4567/api/v1/sveriges-radio/songs/132')
    getLyrics('http://localhost:4567/api/v1/lyrics/getLyricsRadio/132')
})

getP2.addEventListener('click', () => {
    getData('http://localhost:4567/api/v1/sveriges-radio/songs/163')
    getLyrics('http://localhost:4567/api/v1/lyrics/getLyricsRadio/163')
})

getP3.addEventListener('click', () => {
    getData('http://localhost:4567/api/v1/sveriges-radio/songs/164')
    getLyrics('http://localhost:4567/api/v1/lyrics/getLyricsRadio/164')
})

getP4.addEventListener('click', () => {
    getData('http://localhost:4567/api/v1/sveriges-radio/songs/207')
    getLyrics('http://localhost:4567/api/v1/lyrics/getLyricsRadio/207')
})

//Hämtar session ID och skriver ut den på webbsidan

const getMySessionID = document.querySelector('#get_session_id');

const getSessionID = (url) => {
    $(document).ready(function(){
        $.ajax({
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            type: 'GET',
            url: url,
        }).then(function(data) {
            $('.my_session_id').html("Session ID:  " + data.session_id);
        });
    });
    console.log(url);
};

//Trycker man på session ID knappen får man ut session ID

getMySessionID.addEventListener('click', () => {
    getSessionID('http://localhost:4567/api/v1/spotify/session')
})

//En funktion som möjliggör hämtandet av låt URI

const getMyTrackURI = document.querySelector('#get_track_uri');

const getTrackURI =(url)=> {
    $(document).ready(function(){
        $.ajax({
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            type: 'GET',
            url: url,
        }).done(function(data) {
            $('.my_track_uri').html('Låt URI: ' + data.track_uri);
        })
        .fail(function(xhr, status, error) {
          //Uppmanar användaren att logga in först
          var errorMessage = xhr.statusText
          alert('1. Logga in på spotify först! \n 2. Välj en kanal');
        });
    });
    console.log(url);
};

//Hämtar track URI

getMyTrackURI.addEventListener('click', () => {
    getTrackURI('http://localhost:4567/api/v1/spotify/getsearch')
})

//Här skapas variabler för Spotify-spellistor

const getMyPlaylist = document.querySelector('#get_playlist');

//Här hämta vi data från vår JSON-fil och plockar ut det objekt som vi vill ska visa sig på sidan och vi bestämmer även vart objekten ska någonstans

const getPlaylist = (url) => {
    $(document).ready(function(){
        $.ajax({
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            type: 'GET',
            url: url,
        }).done(function(data) {
            $('.my_playlist').html("Spellista:  " + data.arrayOfPlaylists[0].playlist_name + " Spellista ID: " + data.arrayOfPlaylists[0].playlist_id);
        })
        .fail(function(xhr, status, error) {
          //Uppmanar användaren att logga in först
          var errorMessage = xhr.statusText
          alert('Logga in på Spotify först!');
        });
    });
    console.log(url);
};

//Hämtar den första spellistan man har på spotify

getMyPlaylist.addEventListener('click', () => {
    getPlaylist('http://localhost:4567/api/v1/spotify/myplaylists/1')
})

//En funktion som sparar ner variablerna i input-fälten och skickar det till vår endpoint

const postMyData = document.querySelector('#send');

const postData =(url)=> {
    var session = $('#session_id_my_input').val();
    var playlist = $('#playlist_id_my_input').val();
    var track = $('#song_uri_my_input').val();
      $.post(url, {session_id: session, playlist_id: playlist, song_uri: track},function(result) {
        alert(result);
    });
};

//Skickar datan till vår endpoint som gör att man kan lägga till en låt på sin spellista

postMyData.addEventListener('click', () => {
    postData('http://localhost:4567/api/v1/spotify/addsongplaylist')
})