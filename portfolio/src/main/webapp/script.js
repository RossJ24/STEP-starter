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
 * Fetches a greeting with my name from the server.
 */
async function getGreeting(){
 let res = await fetch('/data');
 let text = await res.text();
 addGreetingToDOM(text);
}

/**
 * Fetches greetings with my name from the server.
 */
async function getGreetings(){
 let res = await fetch('/data');
 let list = await res.json();
 let text ='';
 for(obj of list){
    text += obj+"\n";
 }
 addGreetingToDOM(text);
}


/**
 * Add's the greeting to the DOM
 */
 function addGreetingToDOM(greeting){
    document.getElementById("greet").innerText = greeting;
 }