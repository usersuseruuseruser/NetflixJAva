import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import {BrowserRouter} from "react-router-dom";
import Modal from "react-modal";
import {DataStoreProvider} from "./store/dataStoreProvider.jsx";

ReactDOM.createRoot(document.getElementById('root')).render(
    <BrowserRouter>
        <DataStoreProvider>
          <React.StrictMode>
            <App />
          </React.StrictMode>
        </DataStoreProvider>
    </BrowserRouter>,
)
