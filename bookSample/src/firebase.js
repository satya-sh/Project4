import firebase from 'firebase/compat/app';
import 'firebase/compat/auth';

const firebaseConfig = {
    apiKey: "AIzaSyDA5fETL0mQ5LspBpywgCqdUFCKbLzrfkk",
    authDomain: "filtered-search.firebaseapp.com",
    projectId: "filtered-search",
    storageBucket: "filtered-search.appspot.com",
    messagingSenderId: "579002251149",
    appId: "1:579002251149:web:8d2feccf7ef71957952fc4",
    measurementId: "G-JCRMNNWZC4"
};

firebase.initializeApp(firebaseConfig);

export const auth = firebase.auth();
export const googleProvider = new firebase.auth.GoogleAuthProvider();