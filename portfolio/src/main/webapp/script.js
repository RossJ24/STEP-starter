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
 * Adds a random greeting to the page.
 */
function randomFactAboutRoss() {
  const fax =
      ['I am conversational in Chinese!', 'My first programming language was Java', 'The programming language I like the most right now is Dart', 'The programming language I want to learn next is Golang!', 'My favorite basketball team is the Lakers'];

  // Pick a random fact.
  const greeting = fax[Math.floor(Math.random() * fax.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}
