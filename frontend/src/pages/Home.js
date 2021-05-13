import React, { useRef } from "react";
import {
    InputGroup,
    FormControl,
    Button
} from "react-bootstrap";


const Home = () => {
    const searchBar = useRef();

    const handleClick = () => {
        const value = searchBar.current.value;

        window.location.href = `https://www.google.com/search?q=${value}`;
        // fetch(`https://www.google.com/search?q=${value}`)
        //     .then(response => response.json())
        //     .then(data => {
        //         console.log(data);
        //     });
    }
 
    return (
        <>
        <InputGroup className="mb-3">
            <FormControl
                ref={searchBar}
                placeholder="Recipient's username"
                aria-label="Recipient's username"
                aria-describedby="basic-addon2"
            />
            <InputGroup.Append>
                <Button variant="outline-secondary" onClick={handleClick}>Button</Button>
            </InputGroup.Append>
        </InputGroup>
        </>
    )
}

export default Home;
