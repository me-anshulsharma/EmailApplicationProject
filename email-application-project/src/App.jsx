import { useState } from 'react'
import { Toaster } from 'react-hot-toast';
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import EmailSender from './components/EmailSender'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <EmailSender />
      <Toaster></Toaster>
    </>
  )
}

export default App
