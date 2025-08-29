import eslint from '@eslint/js';
import globals from 'globals';
import reactHooks from 'eslint-plugin-react-hooks';
import reactRefresh from 'eslint-plugin-react-refresh';
import react from 'eslint-plugin-react';
import jsxA11y from 'eslint-plugin-jsx-a11y';
import tseslint from 'typescript-eslint';
import { globalIgnores } from 'eslint/config';
import tanstackPluginRouter from '@tanstack/eslint-plugin-router';
import tanstackPluginQuery from '@tanstack/eslint-plugin-query';
import eslintConfigPrettier from 'eslint-config-prettier';

export default tseslint.config([
  // Ignore build and vendor output
  globalIgnores(['dist', 'build', 'coverage', 'node_modules']),

  // Base JS rules
  eslint.configs.recommended,

  // Strong, type-aware TS rules
  ...tseslint.configs.strictTypeChecked,
  ...tseslint.configs.stylisticTypeChecked,

  // React hooks and Vite react-refresh rules
  reactHooks.configs['recommended-latest'],
  reactRefresh.configs.vite,

  // Accessibility rules
  jsxA11y.flatConfigs.recommended,

  // TanStack plugins (Router + Query)
  ...tanstackPluginRouter.configs['flat/recommended'],
  ...tanstackPluginQuery.configs['flat/recommended'],

  // Disable prettier rules
  eslintConfigPrettier,

  // Project-specific settings and extra rules
  {
    files: ['**/*.{ts,tsx,js,jsx}'],
    languageOptions: {
      ecmaVersion: 2022,
      sourceType: 'module',
      globals: {
        ...globals.browser,
      },
      parserOptions: {
        // Enable type-aware linting without specifying explicit project paths
        projectService: true,
        tsconfigRootDir: process.cwd(),
        ecmaFeatures: { jsx: true },
      },
    },
    plugins: {
      react,
    },
    settings: {
      react: {
        version: 'detect',
      },
    },
    rules: {
      // TS: enforce consistency and safer async
      '@typescript-eslint/consistent-type-definitions': ['error', 'type'],
      '@typescript-eslint/consistent-type-imports': [
        'error',
        {
          prefer: 'type-imports',
          disallowTypeAnnotations: false,
          fixStyle: 'inline-type-imports',
        },
      ],
      '@typescript-eslint/no-floating-promises': ['error', { ignoreIIFE: true }],
      '@typescript-eslint/no-misused-promises': [
        'error',
        { checksVoidReturn: { attributes: false } },
      ],
      '@typescript-eslint/prefer-nullish-coalescing': 'warn',
      '@typescript-eslint/prefer-optional-chain': 'warn',

      // React: a few high-signal rules (react-hooks recommended already applied above)
      'react/jsx-no-target-blank': 'error',
      'react/self-closing-comp': 'warn',
      'react/no-unknown-property': ['error', { ignore: ['css'] }],
      '@typescript-eslint/no-unused-vars': [
        'error',
        {
          args: 'all',
          argsIgnorePattern: '^_',
          caughtErrors: 'all',
          caughtErrorsIgnorePattern: '^_',
          destructuredArrayIgnorePattern: '^_',
          varsIgnorePattern: '^_',
          ignoreRestSiblings: true,
        },
      ],
    },
  },

  // Node context for config/build files
  {
    files: ['*.config.{js,ts}', 'vite.config.ts'],
    languageOptions: {
      globals: {
        ...globals.node,
      },
    },
  },
]);
