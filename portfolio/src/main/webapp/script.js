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
  const fact = facts[Math.floor(Math.random() * facts.length)];
  if(fact === 'I have been to 4 continents!' || fact === 'I have been to more than 10 countries!') {
    showMap();
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
    let limit = document.getElementById('limit').value;
    limit = limit.toString();
    let res = await fetch('/data?limit='+limit); 
    let list = await res.json();
    let text = '';
    for(obj of list) {
        text += obj + "\n";
    }
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
  * Delete's all comments from the server
 */
 async function deleteComments(){
    let req = await fetch('/delete-data', {method:'POST'});
    await getComments(document.getElementById("limit").value);
 }

// Function that shows the 
 function showMap(){
     const map = new google.maps.Map(
      document.getElementById('map'),
      {center: {lat: 40.814759, lng: -74.190634}, zoom: 16});
 }