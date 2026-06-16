(function(win, doc) {

var loaded = {},
loadList = {},
loadingFiles = {},
config = {
    autoLoad: true,
    timeout: 6000,
    coreLib: ['http://t.douban.com/js/jquery.min.js'],
    base: '',
    mods: {}
},
jsSelf = (function() { 
  var files = doc.getElementsByTagName('script'); 
  return files[files.length - 1];
})(),
globalList = [],
extConfig,
readyList = [],
isReady = false,
publicData = {},
publicDataStack = {},

isArray = function(e) { 
  return e.constructor === Array; 
},

getMod = function(e) {
 var mods = config.mods, mod; 
 if (typeof e === 'string') {
   mod = (mods[e])? mods[e] : { path: e };
 } else {
   mod = e;
 }
 return mod;
},

load = function(url, type, charset, cb) {
    var wait, n, t, img, 

    done = function() {
      loaded[url] = 1;
      cb && cb(url);
      cb = null;
      win.clearTimeout(wait);
    };

    if (!url) {
        return;
    }

    if (loaded[url]) {
        loadingFiles[url] = false;
        if (cb) {
            cb(url);
        }
        return;
    }

    if (loadingFiles[url]) {
        setTimeout(function() {
            load(url, type, charset, cb);
        }, 10);
        return;
    }

    loadingFiles[url] = true;

    wait = win.setTimeout(function() {
      if (config.timeoutCallback) {
        try {
          config.timeoutCallback(url); 
        } catch(ex) {}
      }
    }, config.timeout);

    t =  type || url.toLowerCase().split(/\./).pop().replace(/[\?#].*/, '');

    if (t === 'js') {
      n = doc.createElement('script');
      n.setAttribute('type', 'text/javascript');
      n.setAttribute('src', config.base+url);
      n.setAttribute('async', true);
    } else if (t === 'css') {
      n = doc.createElement('link');
      n.setAttribute('type', 'text/css');
      n.setAttribute('rel', 'stylesheet');
      n.setAttribute('href', config.base+url);
    }

    if (charset) {
      n.charset = charset;
    }

    if (t === 'css') {
	  document.getElementsByTagName("head")[0].appendChild(n);
      img = new Image();
      img.onerror = function() {
        done();
        img.onerror = null;
        img = null;
      }
      img.src = config.base+url;
    } else {
      n.onerror = function() {
        done();
        n.onerror = null;
      };

      n.onload = n.onreadystatechange = function() {
        var url;
        if (!this.readyState ||
            this.readyState === 'loaded' ||
            this.readyState === 'complete') {
          done();
          n.onload = n.onreadystatechange = null;
        }
      };
    }

    jsSelf.parentNode.insertBefore(n, jsSelf);
},

loadDeps = function(deps, cb) {
    var mods = config.mods, 
    id, m, mod, i = 0, len;

    id = deps.join('');
    len = deps.length;

    if (loadList[id]) {
      cb();
      return;
    }

    function callback() {
      if(!--len) {
        loadList[id] = 1;
        cb();
      }
    }

    for (; m = deps[i++]; ) {
      mod = getMod(m);
      if (mod.requires) {
        loadDeps(mod.requires, (function(mod){
              return function(){
              load(mod.path, mod.type, mod.charset, callback);
              };
              })(mod));
      } else {
        load(mod.path, mod.type, mod.charset, callback);
      }
    }
  },

contentLoaded = function(fn) {
    var done = false, top = true, 
    doc = win.document, 
    root = doc.documentElement,
    add = doc.addEventListener ? 'addEventListener' : 'attachEvent',
    rem = doc.addEventListener ? 'removeEventListener' : 'detachEvent',
    pre = doc.addEventListener ? '' : 'on',

    init = function(e) {
      if (e.type == 'readystatechange' && doc.readyState != 'complete') return;
      (e.type == 'load' ? win : doc)[rem](pre + e.type, init, false);
      if (!done && (done = true)) fn.call(win, e.type || e);
    },

    poll = function() {
      try { root.doScroll('left'); } catch(e) { setTimeout(poll, 50); return; }
      init('poll');
    };

    if (doc.readyState == 'complete') fn.call(win, 'lazy');
    else {
      if (doc.createEventObject && root.doScroll) {
        try { top = !win.frameElement; } catch(e) { }
        if (top) {
          poll();
        }
      }
      doc[add](pre + 'DOMContentLoaded', init, false);
      doc[add](pre + 'readystatechange', init, false);
      win[add](pre + 'load', init, false);
    }
  },

fireReadyList = function() {
    var i = 0, list;
    if (readyList.length) {
      for(; list = readyList[i++]; ) {
        d.apply(this, list);
      }
    }
  },

d = function() {
    var args = [].slice.call(arguments), fn, id;

    if (config.autoLoad &&
        !loadList[config.coreLib.join('')]) {
      loadDeps(config.coreLib, function(){
          d.apply(null, args);
          });
      return;
    }

    if (globalList.length > 0 &&
        !loadList[globalList.join('')]) {
      loadDeps(globalList, function(){
          d.apply(null, args);
          });
      return;
    }

    if (typeof args[args.length - 1] === 'function' ) {
      fn = args.pop();
    }

    id = args.join('');

    if ((args.length === 0 || loadList[id]) && fn) {
      fn();
      return;
    }

    loadDeps(args, function() {
        loadList[id] = 1;
        fn && fn();
        });
  };

d.add = function(sName, oConfig) {
  if (!sName || !oConfig || !oConfig.path) {
    return;
  }
  config.mods[sName] = oConfig;
};

d.delay = function() {
  var args = [].slice.call(arguments), delay = args.shift();
  win.setTimeout(function() {
      d.apply(this, args);
      }, delay);
};

d.global = function() {
  var args = isArray(arguments[0])? arguments[0] : [].slice.call(arguments);
  globalList = globalList.concat(args);
};

d.ready = function() {
  var args = [].slice.call(arguments);
  if (isReady) {
    return d.apply(this, args);
  }
  readyList.push(args);
};

d.css = function(s) {
  var css = doc.getElementById('do-inline-css');
  if (!css) {
    css = doc.createElement('style');
    css.type = 'text/css';
    css.id = 'do-inline-css';
    jsSelf.parentNode.insertBefore(css, jsSelf);
  }

  if (css.styleSheet) {
    css.styleSheet.cssText = css.styleSheet.cssText + s;
  } else {
    css.appendChild(doc.createTextNode(s));
  }
};

d.setData = d.setPublicData = function(prop, value) {
  var cbStack = publicDataStack[prop];

  publicData[prop] = value;

  if (!cbStack) {
    return;
  }

  while (cbStack.length > 0) {
    (cbStack.pop()).call(this, value);
  }
};

d.getData = d.getPublicData = function(prop, cb) {
  if (publicData[prop]) {
    cb(publicData[prop]);
    return;
  } 

  if (!publicDataStack[prop]) {
    publicDataStack[prop] = [];
  }

  publicDataStack[prop].push(function(value){
      cb(value);
      });
};

d.setConfig = function(n, v) {
  config[n] = v;
  return d;
};

d.getConfig = function(n) {
  return config[n];
};

win.Do = d;

contentLoaded(function() {
  isReady = true;
  fireReadyList();
});

extConfig = jsSelf.getAttribute('data-cfg-autoload');
if (extConfig) {
  config.autoLoad = (extConfig.toLowerCase() === 'true') ? true : false;
}

extConfig = jsSelf.getAttribute('data-cfg-corelib');
if (extConfig) {
  config.coreLib = extConfig.split(',');
}

})(window, document);