// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.


const continent_comment_index = 2;
const country_comment_index = 3;
const previouslyVisitedLocations = {
    "home" : {lat: 37.422, lng: -122.084},
    "westmoreland" : {lat: 18.095945, lng: -77.897131},
    "london" : {lat: 51.488114, lng: -0.156856},
    "beijing" : {lat: 39.900630, lng: 116.397424},
    "guilin" : {lat: 25.241825, lng: 110.300425},
    "eiffel" : {lat: 48.858174, lng: 2.294270},
    "niagara" : {lat: 43.101995, lng: -79.085296},
    "haiti" : {lat:18.543493, lng: -72.285923},
    "cancun" : {lat: 21.152070, lng: -86.850266}
  }

/**
 * Adds a random fact about me to the page.
 */
function getRandomFactAboutRoss() {
  const facts = [
    'I am conversational in Chinese!', 
    'I have been to 4 continents!',
    'I have been to more than 10 countries!',
    'I really like pointers and pointer arithmetic!',
    'I was a Track and Field All-American in 2018!',
    'My first programming language was Java!', 
    'The programming language I like the most right now is Dart!', 
    'The programming language I want to learn next is Golang!', 
    'My favorite basketball team is the Lakers!'
        ];
  // Pick a random fact.
  let currentFactIndex = Math.floor(Math.random() * facts.length);
  const fact = facts[currentFactIndex];
  let mapBox = document.getElementById("map")
  //Only shows the map if the comment is relevant to places I have visted
  if(currentFactIndex === continent_comment_index || currentFactIndex === country_comment_index) {
    mapBox.style.width = "400px"; 
    mapBox.style.height = "400px";
    showMap();
  }
  else {
    mapBox.style.width = "0px"; 
    mapBox.style.height = "0px";
  }
  // Add it to the page.
  const factContainer = document.getElementById('fact-container');
  factContainer.innerText = fact;
}

/**
 * Opens my LinkedIn profile in another tab.
 */
function openLinkedInUrl() {
    window.open("https://www.linkedin.com/in/ross-johnson24","_blank");
}

/**
 * Opens my GitHub profile in another tab.
 */
function openGitHubUrl() { 
    window.open("https://github.com/RossJ24","_blank");
}

/**
 * Fetches previous comments from the server.
 */
async function getComments(){
  let limit = document.getElementById('limit').value.toString();
  let res = await fetch(`/data?limit=${limit}`); 
  let list = await res.json();
  let text = list.join("\n");
  addCommentsToDom(text);
}

/**
 * Add's the comments to the DOM
 * @param comments The comments that were fetched from the server 
 */
 function addCommentsToDom(comments){
    document.getElementById("comment-list").innerText = comments;
 }

/**
  * Deletes all comments from the server
  */
 async function deleteComments(){
    let req = await fetch('/delete-data', {method:'POST'});
    await getComments();
 }

// Function that shows the Map
function showMap() {
  const map = new google.maps.Map(
      document.getElementById('map'),
      {center: previouslyVisitedLocations["home"], zoom: 4});
  for(const location in previouslyVisitedLocations){
      new google.maps.Marker({position: previouslyVisitedLocations[location], map: map});
  }
}

//Function that authenticates users before shoeing comment content
async function authenticate(){
    let res = await fetch("/auth");
    let contentType = res.headers.get("content-type");
    if(contentType === "application/json") {
        let userEmail = await res.json();
    }
    else {
        let responseText = await res.text();
        let loginHtml = responseText;
        document.getElementsByTagName("body")[0].innerHTML = responseText;
    }
}
