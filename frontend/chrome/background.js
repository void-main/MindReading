var putToCloud = function(url) {
  var xmlhttp = new XMLHttpRequest();
  xmlhttp.open('PUT', 'http://mindreading.heroku.com/voidmain/test?url=' + url);
  xmlhttp.send(null);
};

chrome.tabs.onUpdated.addListener(function(tabId, changeInfo, tab) {
  putToCloud(tab.url);
});

chrome.tabs.onCreated.addListener(function(tabId, changeInfo, tab) {
  putToCloud(tab.url);
});
