const algoliasearch = require('algoliasearch');
const dotenv = require('dotenv');
const firebase = require('firebase');

// load values from the .env file in this directory into process.env
dotenv.load();

// configure firebase
firebase.initializeApp({
  databaseURL: process.env.FIREBASE_DATABASE_URL,
});
const database = firebase.database();

// configure algolia
const algolia = algoliasearch(
  process.env.ALGOLIA_APP_ID,
  process.env.ALGOLIA_API_KEY
);
const index = algolia.initIndex(process.env.ALGOLIA_INDEX_NAME);



const databaseRef = database.ref('/Database');
databaseRef.on('child_added', addOrUpdateIndexRecord);
databaseRef.on('child_changed', addOrUpdateIndexRecord);
databaseRef.on('child_removed', deleteIndexRecord);

function addOrUpdateIndexRecord(data) {
  // Get Firebase object
  const record = data.val();
  // Specify Algolia's objectID using the Firebase object key
  record.objectID = data.key;
  // Add or update object
  index
    .saveObject(record)
    .then(() => {
      console.log('Firebase object indexed in Algolia', record.objectID);
    })
    .catch(error => {
      console.error('Error when indexing contact into Algolia', error);
      process.exit(1);
    });
}

function deleteIndexRecord(data) {
  // Get Algolia's objectID from the Firebase object key
  const objectID = data.key;
  // Remove the object from Algolia
  index
    .deleteObject(objectID)
    .then(() => {
      console.log('Firebase object deleted from Algolia', objectID);
    })
    .catch(error => {
      console.error('Error when deleting contact from Algolia', error);
      process.exit(1);
    });
}



// Get all contacts from Firebase
database.ref('/Database').once('value', data => {
  // Build an array of all records to push to Algolia
  const records = [];
  data.forEach(data => {
    // get the key and data from the snapshot
    const childKey = data.key;
    const childData = data.val();
    // We set the Algolia objectID as the Firebase .key
    childData.objectID = childKey;
    // Add object for indexing
    records.push(childData);
  });

  // Add or update new objects
  index
    .saveObjects(records)
    .then(() => {
      console.log('Contacts imported into Algolia');
    })
    .catch(error => {
      console.error('Error when importing contact into Algolia', error);
      process.exit(1);
    });
});
