import { useState } from "react";
import axios from "axios";
import isUrl from "is-url";
import "./ShortenLinkGenerator.css";

export const ShortenLinkGenerator = () => {
  const [input, setInput] = useState("");
  const [shortenURL, setShortenURl] = useState("");
  const onButtonClick = (e) => {
    if (input.length > 0 && isUrl(input)) {
      e.preventDefault();
      axios.post("/url", { url: input }).then(
        (response) => {
          setShortenURl(response.data.shortenUrl);
        },
        (error) => {
          console.log(error);
        }
      );
    } else {
      alert("URL not found, please enter a valid URL.");
    }
  };

  return (
    <div className="center-screen">
      <h1>URL SHORTENER SERVICE</h1>
      <div className="shorten-input">
        <div className="ui massive icon input">
          <input
            type="text"
            placeholder={"Enter a URL"}
            value={input}
            onChange={(e) => {
              setInput(e.target.value);
            }}
          />
        </div>
        <br />
        <br />
        <button className="massive ui button" onClick={onButtonClick}>
          Shorten
        </button>
      </div>
      <br />
      {shortenURL && (
        <div className="ui massive icon input">
          <input
            type="text"
            value={`http://localhost:8080/url/${shortenURL}`}
          />
          <button className="ui teal right labeled icon button">
            <i className="copy icon"></i>
            Copy
          </button>
        </div>
      )}
    </div>
  );
};
