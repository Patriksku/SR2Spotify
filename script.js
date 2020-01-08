const getP1 = document.querySelector('#get_p1');
const getP2 = document.querySelector('#get_p2');
const getP3 = document.querySelector('#get_p3');
const getP4 = document.querySelector('#get_p4');
const getMyPlayList_1 = document.querySelector('#get_play_list');
const getMyPlayList_2 = document.querySelector('#get_play_list');
const getMyPlayList_3 = document.querySelector('#get_play_list');
const getMyPlayList_4= document.querySelector('#get_play_list');
const getMyPlayList_5 = document.querySelector('#get_play_list');
const getMyPlayList_6 = document.querySelector('#get_play_list');
const getMyPlayList_7 = document.querySelector('#get_play_list');
const getMyPlayList_8 = document.querySelector('#get_play_list');
const getMyPlayList_9 = document.querySelector('#get_play_list');
const getMyPlayList_10 = document.querySelector('#get_play_list');


const getData = (url) => {
    $(document).ready(function(){
        $.ajax({
            url: url,
        }).then(function(data) {
            $('.channel_id').html("Kanal: " + data.channelname);
            $('.title').html("Nuvarande låt:  " + data.previoustitle);
            $('.artist').html("Artist:  " + data.previousartist);
            $('.album').html("Album:  " + data.previousalbum);
            $('.next_title').html("Nästa låt: " + data.nexttitle);
            $('.next_artist').html("Nästa artist: " + data.nextartist);
            $('.next_album').html("Nästa album:  " + data.nextalbum);
            $('.my_playlist_1').html("Min Spellista:  " + data.arrayOfPlaylists[0].playlist_name);
            $('.my_playlist_2').html("Min Spellista:  " + data.arrayOfPlaylists[1].playlist_name);
            $('.my_playlist_3').html("Min Spellista:  " + data.arrayOfPlaylists[2].playlist_name);
            $('.my_playlist_4').html("Min Spellista:  " + data.arrayOfPlaylists[3].playlist_name);
            $('.my_playlist_5').html("Min Spellista:  " + data.arrayOfPlaylists[4].playlist_name);
            $('.my_playlist_6').html("Min Spellista:  " + data.arrayOfPlaylists[5].playlist_name);
            $('.my_playlist_7').html("Min Spellista:  " + data.arrayOfPlaylists[6].playlist_name);
            $('.my_playlist_8').html("Min Spellista:  " + data.arrayOfPlaylists[7].playlist_name);
            $('.my_playlist_9').html("Min Spellista:  " + data.arrayOfPlaylists[8].playlist_name);
            $('.my_playlist_10').html("Min Spellista:  " + data.arrayOfPlaylists[9].playlist_name);
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

getMyPlayList_1.addEventListener('click', () => {
    getData('http://localhost:4567/api/spotify/getmyplaylists/10')
})

getMyPlayList_2.addEventListener('click', () => {
    getData('http://localhost:4567/api/spotify/getmyplaylists/10')
})

getMyPlayList_3.addEventListener('click', () => {
    getData('http://localhost:4567/api/spotify/getmyplaylists/10')
})

getMyPlayList_4.addEventListener('click', () => {
    getData('http://localhost:4567/api/spotify/getmyplaylists/10')
})

getMyPlayList_5.addEventListener('click', () => {
    getData('http://localhost:4567/api/spotify/getmyplaylists/10')
})

getMyPlayList_6.addEventListener('click', () => {
    getData('http://localhost:4567/api/spotify/getmyplaylists/10')
})

getMyPlayList_7.addEventListener('click', () => {
    getData('http://localhost:4567/api/spotify/getmyplaylists/10')
})

getMyPlayList_8.addEventListener('click', () => {
    getData('http://localhost:4567/api/spotify/getmyplaylists/10')
})

getMyPlayList_9.addEventListener('click', () => {
    getData('http://localhost:4567/api/spotify/getmyplaylists/10')
})

getMyPlayList_10.addEventListener('click', () => {
    getData('http://localhost:4567/api/spotify/getmyplaylists/10')
})