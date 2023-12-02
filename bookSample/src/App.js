// src/App.js
import React, { useState, useEffect } from 'react';
import { auth, googleProvider } from './firebase';
import axios from 'axios';
import './App.css';

function App() {
  // State variables
  const [user, setUser] = useState(null);
  const [phrases] = useState([
    'Change the world from here',
    'Be the change you wish to see',
    'Turn your wounds into wisdom',
  ]);
  const [phrase, setPhrase] = useState('');
  const [hiddenPhrase, setHiddenPhrase] = useState('');
  const [previousGuesses, setPreviousGuesses] = useState('');
  const [maxGuesses] = useState(7);
  const [wrongGuesses, setWrongGuesses] = useState(0);
  const [gameOver, setGameOver] = useState(false);
  const [solved, setSolved] = useState(false);
  const [newHandle, setNewHandle] = useState('');

  // Function to generate a hidden version of the phrase
  const generateHiddenPhrase = (currentPhrase) => {
    const updatedHiddenPhrase = currentPhrase
      .split('')
      .map((char) => (char.match(/[a-zA-Z]/) ? '*' : char))
      .join('');
    setHiddenPhrase(updatedHiddenPhrase);
  };

  // Function to process a player's guess
  const processGuess = (guess) => {
    if (previousGuesses.includes(guess.toLowerCase())) {
      // Check if the guess has already been used
      alert('You have already tried this.');
      return;
    }

    if (phrase.toLowerCase().includes(guess.toLowerCase())) {
      const updatedHiddenPhrase = hiddenPhrase
        .split('')
        .map((char, index) => (phrase[index].toLowerCase() === guess.toLowerCase() ? phrase[index] : char))
        .join('');
      setHiddenPhrase(updatedHiddenPhrase);

      if (updatedHiddenPhrase.toLowerCase() === phrase.toLowerCase()) {
        setGameOver(true); // The game is won
        setSolved(true);
      }
    } else {
      setWrongGuesses(wrongGuesses + 1);

      if (wrongGuesses >= maxGuesses - 1) {
        setGameOver(true); // The game is over
      }
    }

    setPreviousGuesses(previousGuesses + guess.toLowerCase() + ',');
  };

  // Function to start a new game
  const newGame = () => {
    setHiddenPhrase('');
    setPreviousGuesses('');
    setWrongGuesses(0);
    setGameOver(false);
    setSolved(false);

    const randomIndex = Math.floor(Math.random() * phrases.length);
    setPhrase(phrases[randomIndex]);
    generateHiddenPhrase(phrases[randomIndex]);
  };

  // Component did mount
  useEffect(() => {
    newGame(); // Initialize the game with a random phrase
  }, [phrases]);

  // Function to handle Google sign-in
  const signInWithGoogle = async () => {
    try {
      const result = await auth.signInWithPopup(googleProvider);
      const { uid, displayName } = result.user;

      // Check if the user already exists in the database
      const existsResponse = await axios.get(`https://nth-guide-404519.uw.r.appspot.com/exists/${uid}`);

      if (!existsResponse.data) {
        // If the user doesn't exist, add them to the database
        await axios.post('https://nth-guide-404519.uw.r.appspot.com/users', {
          googleId: uid,
          userHandle: displayName,
        });
      }

      setUser(result.user);
    } catch (error) {
      console.error('Google sign-in error:', error);
    }
  };

  useEffect(() => {
    // Listen for changes in authentication state
    const unsubscribe = auth.onAuthStateChanged((authUser) => {
      setUser(authUser);
    });

    // Clean up the listener on component unmount
    return () => {
      unsubscribe();
    };
  }, []);

  // Function to calculate the game score (replace this with your logic)
  const calculateGameScore = () => {
    // Replace this with your game score calculation logic
    return 0;
  };

  // Function to save the game
  const saveGame = async () => {
    if (user) {
      try {
        // Prepare the game record data
        const gameRecord = {
          userHandle: user.displayName,
          googleId: user.uid,
          gameScore: calculateGameScore(),
          createdAt: new Date().toISOString(),
        };

        // Make a POST request to save the game
        await axios.post('https://nth-guide-404519.uw.r.appspot.com/game-records', gameRecord);

        // Optionally, you can show a success message or update the UI
        console.log('Game saved successfully!');
      } catch (error) {
        console.error('Error saving the game:', error);
        // Handle errors appropriately
      }
    }
  };

  // Function to fetch user games
  const fetchUserGames = async () => {
    if (user) {
      try {
        const response = await axios.get(`https://nth-guide-404519.uw.r.appspot.com/game-records/user/${user.uid}/previous-scores`);

        const userGames = response.data;
        console.log('User Games:', userGames);
        // Update the UI to display user games (you can use state or a modal)
      } catch (error) {
        console.error('Error fetching user games:', error);
        // Handle errors appropriately
      }
    }
  };

  // Function to fetch the leaderboard
  const fetchLeaderboard = async () => {
    try {
      const response = await axios.get('https://nth-guide-404519.uw.r.appspot.com/game-records/top');

      const leaderboard = response.data;
      console.log('Leaderboard:', leaderboard);
      // Update the UI to display the leaderboard (you can use state or a modal)
    } catch (error) {
      console.error('Error fetching leaderboard:', error);
      // Handle errors appropriately
    }
  };

  // Function to update the user handle
  const updateHandle = async () => {
    if (user && newHandle.trim() !== '') {
      try {
        // Make a PATCH request to update the user handle
        await axios.patch(`https://nth-guide-404519.uw.r.appspot.com/users/${user.uid}/update-handle`, newHandle);

        // Optionally, you can show a success message or update the UI
        console.log('User handle updated successfully!');
      } catch (error) {
        console.error('Error updating user handle:', error);
        // Handle errors appropriately
      }
    }
  };

  // Function to sign out
  const signOut = () => {
    auth.signOut();
    setUser(null); // Initialize the variables
  };

  return (
    <div className="App">
      {!user ? (
        <div>
          <h1>Welcome to Wheel of Fortune</h1>
          <button onClick={signInWithGoogle}>Sign in with Google</button>
        </div>
      ) : (
        <div>
          {!gameOver ? (
            <div>
              <h1>Wheel of Fortune</h1>
              <div className="phrase">{hiddenPhrase}</div>
              <div className="previous-guesses">Previous Guesses: {previousGuesses}</div>
              <input
                type="text"
                maxLength="1"
                onChange={(e) => {
                  const guess = e.target.value;
                  if (guess.match(/[a-zA-Z]/) && guess.length === 1) {
                    processGuess(guess);
                    e.target.value = '';
                  } else {
                    alert('Please enter Alphabet only');
                    e.target.value = '';
                  }
                }}
              />
              <div className="wrong-guesses">Wrong Guesses: {wrongGuesses}</div>
            </div>
          ) : (
            <div className="end-game-message">
              {solved ? (
                <div className="win-message">YOU WON!!</div>
              ) : (
                <div className="lose-message">Game Over!</div>
              )}
              <button onClick={newGame}>New Game</button>
              <button onClick={saveGame}>Save Game</button>
              <button onClick={fetchUserGames}>My Games</button>
              <button onClick={fetchLeaderboard}>Leaderboard</button>
              <div>
                <input
                  type="text"
                  value={newHandle}
                  onChange={(e) => setNewHandle(e.target.value)}
                  placeholder="Enter new handle"
                />
                <button onClick={updateHandle}>Change Handle</button>
              </div>
              <button onClick={signOut}>Sign Out</button>
            </div>
          )}
        </div>
      )}
    </div>
  );
}

export default App;
