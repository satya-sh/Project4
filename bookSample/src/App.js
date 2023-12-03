import React, { useEffect, useState } from "react";
import "./App.css";
import axios from "axios";

// Firebase imports
import { initializeApp } from "firebase/app";
import { getAuth, signInWithPopup, GoogleAuthProvider } from "firebase/auth";

function App() {
  // State variables
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [userId, setUserId] = useState("");
  const [pageNo, setPageNo] = useState(0);
  const [records, setRecords] = useState([]);
  const [userRecords, setUserRecords] = useState([]);

  // Game-related state variables
  const [phrases] = useState([
    "Change the world from here",
    "Be the change you wish to see",
    "Turn your wounds into wisdom",
  ]);
  const [phrase, setPhrase] = useState("");
  const [hiddenPhrase, setHiddenPhrase] = useState("");
  const [previousGuesses, setPreviousGuesses] = useState("");
  const [maxGuesses] = useState(7);
  const [wrongGuesses, setWrongGuesses] = useState(0);
  const [gameOver, setGameOver] = useState(false);
  const [solved, setSolved] = useState(false);
  const [handle, setHandle] = useState("");

  // Firebase configuration
  const firebaseConfig = {
    apiKey: "AIzaSyDA5fETL0mQ5LspBpywgCqdUFCKbLzrfkk",
    authDomain: "filtered-search.firebaseapp.com",
    projectId: "filtered-search",
    storageBucket: "filtered-search.appspot.com",
    messagingSenderId: "579002251149",
    appId: "1:579002251149:web:8d2feccf7ef71957952fc4",
    measurementId: "G-JCRMNNWZC4"
  };
  initializeApp(firebaseConfig);

  // Function to sign in with Google
  const signInWithGoogle = () => {
    const provider = new GoogleAuthProvider();
    const auth = getAuth();
    signInWithPopup(auth, provider)
      .then((result) => {
        console.log(result.user);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  // useEffect to listen for changes in authentication state
  useEffect(() => {
    const auth = getAuth();
    const unsubscribe = auth.onAuthStateChanged((user) => {
      if (user) {
        setUserId(user.displayName);
      } else {
        console.log("No user is signed in.");
      }
    });
    return () => unsubscribe();
  }, []);

  // Function to display all game records
  function displayAllRecords() {
    axios
      .get("https://nth-guide-404519.uw.r.appspot.com/findAllGameRecord")
      .then((response) => {
        setRecords(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  // Function to display all user records
  function displayAllUserRecords() {
    axios
      .get("https://nth-guide-404519.uw.r.appspot.com/findAllUser")
      .then((response) => {
        setUserRecords(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  // Function to generate hidden phrase
  const generateHiddenPhrase = (currentPhrase) => {
    const updatedHiddenPhrase = currentPhrase
      .split("")
      .map((char) => (char.match(/[a-zA-Z]/) ? "*" : char))
      .join("");
    setHiddenPhrase(updatedHiddenPhrase);
  };

  // Function to process user's guess
  const processGuess = (guess) => {
    if (previousGuesses.includes(guess.toLowerCase())) {
      alert("You have already tried this.");
      return;
    }

    if (phrase.toLowerCase().includes(guess.toLowerCase())) {
      const updatedHiddenPhrase = phrase
        .split("")
        .map((char, index) => {
          if (char.match(/[a-zA-Z]/) && hiddenPhrase[index] === "*") {
            return phrase[index].toLowerCase() === guess.toLowerCase()
              ? phrase[index]
              : "*";
          }
          return char;
        })
        .join("");
      setHiddenPhrase(updatedHiddenPhrase);

      if (updatedHiddenPhrase.toLowerCase() === phrase.toLowerCase()) {
        setGameOver(true);
        setSolved(true);
      }
    } else {
      setWrongGuesses(wrongGuesses + 1);

      if (wrongGuesses >= maxGuesses - 1) {
        setGameOver(true);
      }
    }

    setPreviousGuesses(previousGuesses + guess.toLowerCase() + ",");
  };

  // Function to handle name input change
  const handleNameChange = (event) => {
    setHandle(event.target.value);
  };

  // Function to submit name and save game record
  const handleSubmitName = () => {
    setPageNo(3);
    displayAllRecords();
    displayAllUserRecords();

    const auth = getAuth();
    const user = auth.currentUser;

    // Save user data
    axios
      .post("https://nth-guide-404519.uw.r.appspot.com/saveUser", {
        userId: user.displayName,
        handle: handle,
        email: user.email,
        date: new Date().toISOString().slice(0, 10),
      })
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.error(error);
      });

    // Save game record
    axios
      .post("https://nth-guide-404519.uw.r.appspot.com/saveGameRecord", {
        userId: user.displayName,
        score: maxGuesses - wrongGuesses,
        date: new Date().toISOString().slice(0, 10),
      })
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  // Function to display user's game records
  const displayMyGames = () => {
    axios
      .get(`https://nth-guide-404519.uw.r.appspot.com/findByUserId?userId=${userId}`)
      .then((response) => {
        setRecords(response.data);
        setPageNo(4);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  // Function to display leaderboard
  const displayLeaderboard = () => {
    axios
      .get("https://nth-guide-404519.uw.r.appspot.com/findAllGameRecord")
      .then((response) => {
        setRecords(response.data);
        setPageNo(5);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  // Function to start a new game
  const newGame = () => {
    setHiddenPhrase("");
    setPreviousGuesses("");
    setWrongGuesses(0);
    setGameOver(false);
    setSolved(false);

    const randomIndex = Math.floor(Math.random() * phrases.length);
    setPhrase(phrases[randomIndex]);
    generateHiddenPhrase(phrases[randomIndex]);
  };

  // useEffect to start a new game when phrases change
  useEffect(() => {
    newGame();
  }, [phrases]);

  // JSX rendering
  return (
    <>
      {!userId && (
        <button type="button" onClick={signInWithGoogle}>
          Sign in with Google
        </button>
      )}
      {userId && (
        <div className="App">
          {!gameOver ? (
            // Game in progress
            <div>
              <h1>Wheel of Fortune</h1>
              <div className="phrase">{hiddenPhrase}</div>
              <div className="previous-guesses">
                Previous Guesses: {previousGuesses}
              </div>
              <input
                type="text"
                maxLength="1"
                onChange={(e) => {
                  const guess = e.target.value;
                  if (guess.match(/[a-zA-Z]/) && guess.length === 1) {
                    processGuess(guess);
                    e.target.value = "";
                  } else {
                    alert("Please enter Alphabet only");
                    e.target.value = "";
                  }
                }}
              />
              <div className="wrong-guesses">
                Wrong Guesses: {wrongGuesses}
              </div>
            </div>
          ) : (
            // Game over
            <div className="end-game-message">
              {solved && pageNo === 0 && (
                <div className="win-message">YOU WON!!</div>
              )}
              {!solved && pageNo === 0 && (
                <div className="lose-message">Game Over!</div>
              )}
              {pageNo === 0 && (
                // Prompt to save game record
                <div>
                  <p>Do you want to save your game record?</p>
                  <button type="button" onClick={() => setPageNo(2)}>
                    Yes
                  </button>
                  <button type="button" onClick={() => setPageNo(1)}>
                    No
                  </button>
                </div>
              )}
              {pageNo === 1 && <button onClick={newGame}>New Game</button>}

              {pageNo === 2 && (
                // Form to enter name and save record
                <div>
                  <label htmlFor="nameInput">Enter your name:</label>
                  <input
                    type="text"
                    id="nameInput"
                    value={handle}
                    onChange={handleNameChange}
                  />
                  <button type="button" onClick={handleSubmitName}>
                    Submit Name
                  </button>
                </div>
              )}

              {pageNo === 3 && (
                // Options to display user's games or leaderboard
                <div>
                  <button onClick={displayMyGames}>Display My Games</button>
                  <button onClick={displayLeaderboard}>Display Leaderboard</button>
                </div>
              )}

              {pageNo === 4 &&
                // Displaying user's game records
                userRecords.map((record) => (
                  <div className="record-item" key={record.id}>
                    <h3>{record.id}</h3>
                    <p>by {record.score}</p>
                    <p> {record.date}</p>
                    <p> {record.userId}</p>
                  </div>
                ))}

              {pageNo === 5 &&
                // Displaying leaderboard
                records.map((record) => (
                  <div className="record-item" key={record.id}>
                    <h3>{record.id}</h3>
                    <p>by {record.score}</p>
                    <p> {record.date}</p>
                    <p> {record.userId}</p>
                  </div>
                ))}
            </div>
          )}
        </div>
      )}
    </>
  );
}

export default App;
