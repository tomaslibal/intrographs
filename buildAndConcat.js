var rollup = require('rollup').rollup;
var babel = require('rollup-plugin-babel');

var fs = require('fs');

rollup({
  entry: 'src/demo.js',
  format: 'umd',
  plugins: [
    babel({
      include: ['src/**'],
      exclude: ['build/**', 'dist/**', 'dev/**', 'node_modules/**', 'test/**'],
      sourceMap: false 
    })
  ]
}).then(function(bundle) { 

    const generated = bundle.generate();
    const code = generated.code;

    fs.writeFileSync('dist/out.js', code);

    console.log('Build done. Output saved @ dist/out.js');
});
