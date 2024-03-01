/* jshint esversion: 6 */

document.getElementById('searchbutton').onclick = () => {
    fetch("/search?q=" + document.getElementById('searchbox').value)
    .then((response) => response.json())
    .then((data) => {
        if(data.length == 0)
        {
            message = "No web page contains the query word"
            document.getElementById("responsesize").innerHTML =
                        "<p>" + message + "</p>";
        }else{
            document.getElementById("responsesize").innerHTML =
                    "<p>" + data.length + " websites retrieved</p>";
        }

        let results = data.map((page) =>
            `<li><a href="${page.url}">${page.title}</a></li>`)
            .join("\n");
        document.getElementById("urllist").innerHTML = `<ul>${results}</ul>`;
    });
};