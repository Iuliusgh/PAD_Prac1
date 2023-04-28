import './App.css';
import React, { useState, useEffect } from "react";
import axios from "axios";

function App() {
  const [title, setTitle] = useState("");
  const [author, setAuthor] = useState("");
  const [books, setBooks] = useState([]);
  const [booksEmpty, setBooksEmpty] = useState(false);
  const [loading, setLoading] = useState(false);
  const [search, setSearch] = useState("");
  const [online, setOnline] = useState(navigator.onLine);

  useEffect(() => {
    if (search !== "" && online) {
      setLoading(true);
      axios
        .get(`https://www.googleapis.com/books/v1/volumes?q=${search}&maxResults=40&printType=BOOKS`)
        .then((response) => {
          localStorage.setItem("ant_search", localStorage.getItem("search"));
          localStorage.setItem("ant_author", localStorage.getItem("author"));
          localStorage.setItem("ant_title", localStorage.getItem("title"));
          localStorage.setItem("response_data_items", JSON.stringify(response.data.items));
          localStorage.setItem("response_data_empty", JSON.stringify(response.data.totalItems === 0));
          localStorage.setItem("search", JSON.stringify(search));
          localStorage.setItem("author", JSON.stringify(author));
          localStorage.setItem("title", JSON.stringify(title));
          setBooks(response.data.items);
          setLoading(false);
          setBooksEmpty(response.data.totalItems === 0);
        })
        .catch((error) => {
          console.error(error);
          setLoading(false);
          setBooksEmpty(true);
        });
    }
  }, [search]);

  useEffect(() => {
    const handleOnlineStatus = () => {
      setOnline(navigator.onLine);
    };
    window.addEventListener("online", handleOnlineStatus);
    window.addEventListener("offline", handleOnlineStatus);
    return () => {
      window.removeEventListener("online", handleOnlineStatus);
      window.removeEventListener("offline", handleOnlineStatus);
    };
  }, []);
  
  const handleRetrieve = () => {
    const savedSearch = JSON.parse(localStorage.getItem("ant_search"));
    const savedAuthor = JSON.parse(localStorage.getItem("ant_author"));
    const savedTitle = JSON.parse(localStorage.getItem("ant_title"));
  
    setSearch(savedSearch || "");
    setAuthor(savedAuthor || "");
    setTitle(savedTitle || "");
  };  

  const handleSearch = () => {
    if (title === "" && author === "") {
      window.alert("¡Introduce al menos un campo!");
    }
    else if (author === "") {
      setSearch(`intitle:${title}`);
    }
    else if (title === "") {
      setSearch(`inauthor:${author}`);
    }
    else {
      setSearch(`inauthor:${author}+intitle:${title}`);
    }
  };

  return (
    <div>
      <h1>Buscador de Libros</h1>
      <div className='inputs'>
        <input type="text" id="title" placeholder='Título' value={title} onChange={(e) => setTitle(e.target.value)} />
      </div>
      <div className='inputs'>
        <input type="text" id="author" placeholder='Autor' value={author} onChange={(e) => setAuthor(e.target.value)} />
      </div>
      <div className='inputs'><button onClick={handleSearch}>Buscar</button></div>
      <div className='inputs1'><button onClick={handleRetrieve}>Recuperar</button></div>

      {online ? (
        loading ? (
          <p>Cargando libros...</p>
        ) : (
          booksEmpty ? (
            <p>No se encontraron resultados</p>
          ) : (
            <ul>
              {books.map((book) => (
                <li key={book.id}>
                  <p><a className='enlace' href={book.volumeInfo.infoLink}>{book.volumeInfo.title}</a> - {book.volumeInfo.authors}</p>
                  {book.volumeInfo.imageLinks ? (
                    <a href={book.volumeInfo.infoLink}><img src={book.volumeInfo.imageLinks.thumbnail} alt={`Portada de '${book.volumeInfo.title}'`} /></a>
                  ) : (
                    <p>No image disponible</p>
                  )}
                </li>
              ))}
            </ul>
          )
        )
      ) : (
        <p>No hay conexión a internet</p>
      )}
    </div>
  );
}

export default App;