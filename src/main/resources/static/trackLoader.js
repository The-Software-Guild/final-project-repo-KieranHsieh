
$(document).ready(function() {
    $("#track_table>tbody>tr:first").trigger("click");
});

function loadTrack(trackId) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/tracks/" + trackId,
        success: function(track) {
            var html = "<h2>" + track.name + "</h2>" +
                "<iframe src='https://open.spotify.com/embed/track/" + track.id + "?theme=0' width='100%' height='80' frameborder='0' allowtransparency='true' allow='encrypted-media'></iframe>";
            $("#song_info").empty()
            $("#song_info").append(html)
        }
    })
}